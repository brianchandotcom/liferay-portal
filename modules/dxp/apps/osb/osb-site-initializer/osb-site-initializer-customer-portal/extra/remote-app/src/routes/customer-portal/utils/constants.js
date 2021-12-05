export const status = {
	active: 1,
	expired: 2,
	future: 3,
};

export const CUSTOM_EVENTS = {
	PROJECT: 'customer-portal-project-loading',
	USER_ACCOUNT: 'customer-portal-select-user-loading',
};

const getYearlyTerms = ({endDate, startDate}) => {
	const yearStartDate = new Date(startDate).getFullYear();
	const yearEndDate = new Date(endDate).getFullYear();
	if (yearStartDate + 1 < yearEndDate) {
		const yearDateSplitted = new Array(yearEndDate - yearStartDate + 1)
			.fill()
			.map((_, index, array) => {
				const currentYear = yearStartDate + index;

				const yearNumStartDate = new Date(startDate).setFullYear(
					currentYear
				);
				const yearNumEndDate = currentYear + 1;

				const daysEndDate = new Date(startDate).getDate();
				const monthsEndDate = new Date(startDate).getMonth();

				if (index + 2 >= array.length) {
					const yearNumEndDate = new Date(endDate).setFullYear(
						currentYear + 1
					);

					return index + 2 === array.length
						? {
								endDate: new Date(yearNumEndDate),
								startDate: new Date(yearNumStartDate),
						  }
						: null;
				}

				return {
					endDate: new Date(
						yearNumEndDate,
						monthsEndDate,
						daysEndDate - 1
					),
					startDate: new Date(yearNumStartDate),
				};
			})
			.filter((item) => item);

		return yearDateSplitted;
	}

	return [{endDate, startDate}];
};

export {getYearlyTerms};
