const ACTIVE_INDEX_SESSION_KEY = `${fragmentNamespace}-activeIndex`;

const steps = fragmentElement.querySelectorAll('li');

function getActiveIndexFromSession() {
	return Number(
		Liferay.Util.SessionStorage.getItem(
			ACTIVE_INDEX_SESSION_KEY,
			Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
		)
	);
}

function saveActiveIndexInSession(index) {
	Liferay.Util.SessionStorage.setItem(
		ACTIVE_INDEX_SESSION_KEY,
		index,
		Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
	);
}

function setActiveStep(index) {

	// Deactivate current active step if it exists

	const activeStep = fragmentElement.querySelector('li.active');

	activeStep?.classList.remove('active');

	// Set new active step, save index in session if it's edit mode

	const step = steps[index];

	step.classList.add('active');

	if (layoutMode === 'edit') {
		saveActiveIndexInSession(index);
	}

	Liferay.fire('formFragment:changeStep', {
		emitter: fragmentElement,
		step: index,
	});
}

function main() {

	// Set initial active step, get it from session if it's edit mode

	setActiveStep(layoutMode === 'edit' ? getActiveIndexFromSession() || 0 : 0);

	// Change active step on button click

	for (const [index, step] of steps.entries()) {
		step.querySelector('button').addEventListener('click', () => {
			if (step.classList.contains('active')) {
				return;
			}

			setActiveStep(index);
		});
	}
}

main();
