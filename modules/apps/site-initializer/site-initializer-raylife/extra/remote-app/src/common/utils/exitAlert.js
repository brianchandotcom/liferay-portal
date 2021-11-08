const warnMessage = 'Changes you made may not be saved.';

export const createExitAlert = () => {
	window.onbeforeunload = function (event) {
		event.preventDefault();
		event.returnValue = warnMessage;

		return warnMessage;
	};
};

export const clearExitAlert = () => {
	window.onbeforeunload = function () {};
};
