// Let's keep this validation for now
// eslint-disable-next-line
const toBool = (value) => value && value != undefined && value === 'true';

export const verifyInputAgentPage = (properties, nextSection) => {
	const auxBusiness = properties?.business?.hasSellProductsUnderOwnBrand;
	const auxEmployees = properties?.employees?.hasFein;
	const auxProperty = properties?.property?.isThereDivingBoards;
	let contextualMessage = '';

	if (toBool(auxBusiness) && nextSection.section === 'employees') {
		contextualMessage =
			'We need to ask you for more information about your business.';
	}
	else if (!toBool(auxEmployees) && nextSection.section === 'property') {
		contextualMessage =
			'We need to ask you for more information about your employees.';
	}
	else if (toBool(auxProperty)) {
		contextualMessage =
			'We need to ask you for more information about your business location.';
	}

	return contextualMessage;
};
