const dateToCustomFormat = (rawDate) => {
	const date = new Date(rawDate);

	return date.toLocaleDateString(undefined, {
		day: '2-digit',
		month: 'long',
		year: 'numeric',
	});
};

export default dateToCustomFormat;
