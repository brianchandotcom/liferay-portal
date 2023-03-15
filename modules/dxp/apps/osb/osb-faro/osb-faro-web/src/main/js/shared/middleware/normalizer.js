import {normalize} from 'normalizr';

export default () => next => action => {
	const {meta, payload, type} = action;

	if (payload && meta && meta.schema) {
		const {entities, result} = normalize(payload, meta.schema);

		action = {
			meta,
			payload: {
				entities,
				result
			},
			type
		};
	}

	return next(action);
};
