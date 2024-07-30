const ACTIVE_STEP_ID_SESSION_KEY = `stepperFragment-${fragmentNamespace}-activeStepId`;

function getStepIdFromSession() {
	return Liferay.Util.SessionStorage.getItem(
		ACTIVE_STEP_ID_SESSION_KEY,
		Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
	);
}

function saveStepIdInSession(stepId) {
	Liferay.Util.SessionStorage.setItem(
		ACTIVE_STEP_ID_SESSION_KEY,
		stepId,
		Liferay.Util.SessionStorage.TYPES.PERSONALIZATION
	);
}

function setActiveStep(step) {
	if (step.classList.contains('active')) {
		return;
	}

	// Deactivate current active step if it exists

	const activeStep = fragmentElement.querySelector('li.active');

	activeStep?.classList.remove('active');

	// Set new active step, save id in session if it's edit mode

	step.classList.add('active');

	if (layoutMode === 'edit') {
		saveStepIdInSession(step.id);
	}
}

function main() {

	// Set initial active step, get it from session if it's edit mode

	let activeStep = fragmentElement.querySelector('li');

	const sessionStepId = getStepIdFromSession();

	if (layoutMode === 'edit' && sessionStepId) {
		activeStep = document.getElementById(sessionStepId);
	}

	setActiveStep(activeStep);

	// Change active step on button click

	const steps = fragmentElement.querySelectorAll('li');

	for (const step of steps) {
		step.querySelector('button').addEventListener('click', () =>
			setActiveStep(step)
		);
	}
}

main();
