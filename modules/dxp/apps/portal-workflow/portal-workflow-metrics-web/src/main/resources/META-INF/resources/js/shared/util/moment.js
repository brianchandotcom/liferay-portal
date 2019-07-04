import moment from 'moment/min/moment-with-locales';

moment.locale(Liferay.ThemeDisplay.getBCP47LanguageId());

const momentWithLocale = moment;

export default momentWithLocale;
