export const actionTypes = {
	SET_MAINTENANCE_SEEN: 'SET_MAINTENANCE_SEEN'
};

export function setMaintenanceSeen(payload) {
	return {
		payload,
		type: actionTypes.SET_MAINTENANCE_SEEN
	};
}
