const getDateCustomFormat = (rawDate, format, locale) => {
	const date = new Date(rawDate);

	return date.toLocaleDateString(locale, format);
};

export default getDateCustomFormat;
