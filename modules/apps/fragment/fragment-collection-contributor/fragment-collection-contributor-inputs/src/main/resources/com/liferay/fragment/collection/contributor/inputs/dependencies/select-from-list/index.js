const combobox = fragmentElement.querySelector('.combobox-list');
const inputNode = combobox.querySelector('.combobox-input');
const buttonNode = combobox.querySelector('.combobox-button');
const optionList = combobox.querySelector('.combobox-optionList');

buttonNode.addEventListener("click", toggleCombobox);
buttonNode.addEventListener("blur", handleComboboxBlur);
inputNode.addEventListener("click", toggleCombobox);
inputNode.addEventListener("input", handleInputChange);
inputNode.addEventListener("blur", handleComboboxBlur);
inputNode.addEventListener("keydown", handleComboboxKeyDown);
optionList.addEventListener("click", handleClickOptionList);

const allOptions = Array.from(optionList.getElementsByTagName("LI")).map((option) => {
	return { id: option.id, textContent: option.textContent, textValue: option.textContent.toLowerCase() };
});

function checkIsOpenCombobox() {
	return (
		inputNode.getAttribute("aria-expanded") === "true" &&
		buttonNode.getAttribute("aria-expanded") === "true"
	);
}

function closeCombobox() {
	const currentFocusedOption =
		document.getElementById(optionList.getAttribute(
			"aria-activedescendant"
		));

	optionList.setAttribute("aria-activedescendant", "");

	if (currentFocusedOption) {
		currentFocusedOption.removeAttribute("aria-selected");
	}

	optionList.style.display = "none";

	inputNode.setAttribute("aria-expanded", "false");
	buttonNode.setAttribute("aria-expanded", "false");
}

function createOptionElement(option) {
	const optionElement = document.createElement('li');
	optionElement.id = option.id;
	optionElement.classList.add('dropdown-item');
	optionElement.setAttribute('role', 'option');
	optionElement.textContent = option.textContent;
	return optionElement;
}

function handleClickOptionList(event){
	let optionSelected = null;

	if (event.target.matches('.dropdown-item')) {
		optionSelected = event.target;
	}
	else if (event.target.closest('.dropdown-item')) {
		optionSelected = event.target.closest('.dropdown-item');
	}

	if (optionSelected) {
		setSelectedOption(optionSelected);
	}
}

function handleComboboxBlur() {
	if (checkIsOpenCombobox()) {
		setTimeout(() => closeCombobox(),500);
	}
}

function handleComboboxKeyDown(event) {
	if (!optionList.firstElementChild) {
		return;
	}

	const keyOptions = [
		"ArrowDown", "ArrowUp", "Home", "End"
	]

	const currentFocusedOption =
		document.getElementById(optionList.getAttribute(
			"aria-activedescendant"
		));

	if (keyOptions.includes(event.key)) {
		if (!checkIsOpenCombobox()) {
			openCombobox();
		}

		event.preventDefault();
	}

	switch (event.key) {
		case "ArrowDown":
			if (event.altKey) {
				break;
			}

			if (currentFocusedOption) {
				setActiveDescendantCombobox(currentFocusedOption.nextElementSibling || optionList.firstElementChild);
			}
			else {
				setActiveDescendantCombobox(optionList.firstElementChild);
			}
			break;
		case "ArrowUp":
			if (currentFocusedOption) {
				setActiveDescendantCombobox(currentFocusedOption.previousElementSibling || optionList.lastElementChild);
			}
			else {
				setActiveDescendantCombobox(optionList.lastElementChild);
			}
			break;

		case "Home":
			setActiveDescendantCombobox(optionList.firstElementChild);
			break;
		case "End":
			setActiveDescendantCombobox(optionList.lastElementChild);
			break;
		case "Enter":
			if (!currentFocusedOption) {
				break;
			}

			setSelectedOption(currentFocusedOption);
	}
}

function handleInputChange() {
	if (!checkIsOpenCombobox()) {
		openCombobox();
	}

	optionList.setAttribute("aria-activedescendant", "");

	const filterValue = inputNode.value.toLowerCase();

	optionList.innerHTML = "";

	if (filterValue) {
		allOptions.forEach((option) => {
			if (
				option.textValue.startsWith(filterValue)
			) {
				optionList.appendChild(createOptionElement(option));
			}

		});

		allOptions.forEach((option) => {
			if (
				option.textValue.includes(filterValue)
			) {
				if (!document.getElementById(option.id)) {
					optionList.appendChild(createOptionElement(option));
				}
			}
		});
	}
	else {
		allOptions.forEach((option) => optionList.appendChild(createOptionElement(option)));
	}
}

function openCombobox() {
	optionList.style.display = "block";

	inputNode.setAttribute("aria-expanded", "true");
	buttonNode.setAttribute("aria-expanded", "true");

	handleInputChange();
}

function setActiveDescendantCombobox(option) {
	const currentFocusedOption =
		document.getElementById(optionList.getAttribute(
			"aria-activedescendant"
		));

	if (currentFocusedOption) {
		currentFocusedOption.removeAttribute("aria-selected");
	}

	optionList.setAttribute("aria-activedescendant", option.id);
	option.setAttribute("aria-selected", "true");

	option.scrollIntoView({ block: "end" });
}

function setSelectedOption (option) {
	const hiddenInput = document.getElementById("hiddenInput");
	const selectedOption = document.getElementById(hiddenInput.id);

	if (selectedOption) {
		selectedOption.classList.remove("active");
	}

	option.classList.add("active");

	inputNode.value = option.textContent;
	hiddenInput.value = option.id;

	closeCombobox();
}

function toggleCombobox() {
	if (checkIsOpenCombobox()) {
		closeCombobox();
	}
	else {
		openCombobox();
	}
}
