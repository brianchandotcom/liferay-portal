export const setJSONArrayValue = value => {
	if (value && value.JSONArray) {
		return value.JSONArray;
	}

	return value;
};
