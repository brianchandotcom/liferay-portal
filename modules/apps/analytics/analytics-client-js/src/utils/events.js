const onReady = fn => {
	if (
		document.readyState === 'interactive' ||
		document.readyState === 'complete' ||
		document.readyState === 'loaded'
	) {
		fn();
	} else {
		document.addEventListener('DOMContentLoaded', fn);
	}

	return () => document.removeEventListener('DOMContentLoaded', fn);
};

export {onReady};