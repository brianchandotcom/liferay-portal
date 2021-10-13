import {TOTAL_OF_FIELD} from './constants';

export const isHabitational = (value) => value === 'habitational';

export const isThereSwimming = (value) => value === 'true';

export const propertyTotalFields = (properties) => {
	let fieldCount = TOTAL_OF_FIELD.PROPERTY;

	if (
		properties?.basics?.properties?.segment.toLowerCase() === 'habitational'
	) {
		fieldCount++;
	}
	if (properties?.property?.isThereSwimming === 'true') {
		fieldCount++;
	}

	return fieldCount;
};
