import numeral from 'numeral';

function setupLocale(locale) {
	numeral.register('locale', locale, {
		abbreviations: {
			billion: Liferay.Language.get('billion-abbreviation'),
			million: Liferay.Language.get('million-abbreviation'),
			thousand: Liferay.Language.get('thousand-abbreviation'),
			trillion: Liferay.Language.get('trillion-abbreviation')
		},
		delimiters: {
			decimal: Liferay.Language.get('decimal-delimiter'),
			thousands: Liferay.Language.get('thousands-delimiter')
		}
	});

	numeral.locale(locale);
}

setupLocale(Liferay.ThemeDisplay.getLanguageId());

export function formatNumber(number, pattern) {
	return numeral(number).format(pattern);
}
