export const actionTypes = {
	SET_BACK_URL: 'SET_BACK_URL'
};

export function setBackURL(url) {
	return {
		payload: {url},
		type: actionTypes.SET_BACK_URL
	};
}
