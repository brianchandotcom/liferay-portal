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

function setActiveStep(index, {sendEvent = true} = {}) {

	// Deactivate current active step if it exists

	const activeStep = fragmentElement.querySelector('li.active');

	activeStep?.classList.remove('active');

	// Set new active step, save index in session if it's edit mode

	const step = steps[index];

	step.classList.add('active');

	if (layoutMode === 'edit') {
		saveActiveIndexInSession(index);
	}

	if (sendEvent) {
		Liferay.fire('formFragment:changeStep', {
			emitter: fragmentElement,
			step: index,
		});
	}
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

	Liferay.on('formFragment:changeStep', (event) => {
		if (!event.emitter || event.emitter === fragmentElement) {
			return;
		}

		const form = event.emitter.closest('.lfr-layout-structure-item-form');

		if (!form || !form.contains(fragmentElement)) {
			return;
		}

		const activeIndex = Array.from(steps).findIndex((step) =>
			step.classList.contains('active')
		);

		if (event.step === 'next' && activeIndex <= steps.length - 2) {
			setActiveStep(activeIndex + 1, {sendEvent: false});
		}

		if (event.step === 'previous' && activeIndex !== 0) {
			setActiveStep(activeIndex - 1, {sendEvent: false});
		}
	});
}

main();
