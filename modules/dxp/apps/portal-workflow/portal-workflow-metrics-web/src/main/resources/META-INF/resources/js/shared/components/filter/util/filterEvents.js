const buildFallbackItems = fallbackKeys => {
	if (fallbackKeys) {
		return fallbackKeys.map(key => ({
			active: true,
			key
		}));
	}

	return null;
};

const handleClickOutside = (callback, wrapperRef) => event => {
	const clickOutside = wrapperRef && !wrapperRef.contains(event.target);

	if (clickOutside) {
		callback();
	}
};

const addClickOutsideListener = listener => {
	document.addEventListener('mousedown', listener);
};

const removeClickOutsideListener = listener => {
	document.removeEventListener('mousedown', listener);
};

export {
	addClickOutsideListener,
	buildFallbackItems,
	handleClickOutside,
	removeClickOutsideListener
};
