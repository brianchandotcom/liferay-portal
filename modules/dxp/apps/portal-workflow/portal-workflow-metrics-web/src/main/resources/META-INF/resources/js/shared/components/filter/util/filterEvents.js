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
	handleClickOutside,
	removeClickOutsideListener
};
