import {updateField} from './fieldEditedHandler.es';

export const handleFieldBlurred = (state, event) => {
	const {propertyName, propertyValue} = event;
	let newState = {pages: state.pages};

	if (propertyName === 'name' && propertyValue === '') {
		newState = updateField(state, propertyName, propertyValue);
	}

	return newState;
};

export default handleFieldBlurred;
