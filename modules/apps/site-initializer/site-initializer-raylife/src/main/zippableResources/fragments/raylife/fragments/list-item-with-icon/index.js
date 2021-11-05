const plusIcon = fragmentElement.querySelector('#plus-icon');
const minusIcon = fragmentElement.querySelector('#minus-icon');
const customIcons = fragmentElement.getElementsByClassName('custom-icon');
const faqList = fragmentElement.querySelector('#faq-list');

for (let i = 0; i < customIcons.length; i++) {
	const icon = customIcons[i];

	icon.onclick = function () {
		let flag = collapseDiv.classList.contains('collapse');

		if (flag) {
			collapseDiv.classList.remove('collapse');
			plusIcon.classList.add('d-none');
			minusIcon.classList.remove('d-none');
		}
		else {
			collapseDiv.classList.add('collapse');
			plusIcon.classList.remove('d-none');
			minusIcon.classList.add('d-none');
		}
	};
}
