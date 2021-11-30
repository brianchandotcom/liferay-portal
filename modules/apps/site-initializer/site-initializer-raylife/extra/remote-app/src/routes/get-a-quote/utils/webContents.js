import {toSlug} from '../../../common/utils';

const PRODUCT_QUOTE = [
	'general-liability',
	'professional-liability',
	'workers-compensation',
	'business-owners-policy',
];

export function allowedProductQuote(title) {
	return PRODUCT_QUOTE.includes(toSlug(title));
}
