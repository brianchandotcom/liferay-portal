import {toSlug} from '~/common/utils';

const PRODUCT_QUOTE = [
	'general-liability',
	'professional-liability',
	'workers-compensation',
	'business-owners-policy',
];

export const allowedProductQuote = (title) =>
	PRODUCT_QUOTE.includes(toSlug(title));
