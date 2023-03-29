import {Modal} from 'shared/types';

const actionTypes = Modal.actionTypes;
const modalTypes = Modal.modalTypes;

export {actionTypes, modalTypes};

export const close = () => ({
	type: actionTypes.CLOSE_MODAL
});

export const closeAll = () => ({
	type: actionTypes.CLOSE_ALL_MODALS
});

export const open = (
	type,
	props: {[key: string]: any} = {},
	options: {closeOnBlur?: boolean} = {}
) => {
	const {closeOnBlur = true} = options;

	return {
		payload: {
			closeOnBlur,
			props,
			type
		},
		type: actionTypes.OPEN_MODAL
	};
};
