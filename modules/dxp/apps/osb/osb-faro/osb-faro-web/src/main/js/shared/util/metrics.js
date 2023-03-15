import {Colors} from 'shared/util/charts';

export const Icons = {
	negative: 'caret-bottom-l',
	neutral: undefined,
	positive: 'caret-top-l'
};

/**
 * Return the icon name
 * @param {number} number
 */
export const getIcon = number => {
	if (number > 0) {
		return Icons.positive;
	} else if (number < 0) {
		return Icons.negative;
	} else {
		return Icons.neutral;
	}
};

/**
 * Return the current color
 * @param {string} str
 */
export const getStatsColor = str => {
	if (str) {
		if (str.toLowerCase() == 'positive') {
			return Colors.positive;
		} else if (str.toLowerCase() == 'negative') {
			return Colors.negative;
		} else {
			return Colors.neutral;
		}
	} else {
		return Colors.neutral;
	}
};
