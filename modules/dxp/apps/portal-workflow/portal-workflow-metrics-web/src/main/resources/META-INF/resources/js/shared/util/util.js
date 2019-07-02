/**
 * Returns the percent number passing as
 * parameter the current number and total number.
 * @param {number} number1
 * @param {number} number2
 * @returns {number}
 */
const getPercentage = (number1, number2) => {
	const result = number1 / number2;

	return isValidNumber(result) ? result : 0;
};

/**
 * Return true if number is valid
 * @param {number} number
 */
const isValidNumber = number => {
	return !isNaN(number) && number !== Infinity ? true : false;
};

export {getPercentage, isValidNumber};
