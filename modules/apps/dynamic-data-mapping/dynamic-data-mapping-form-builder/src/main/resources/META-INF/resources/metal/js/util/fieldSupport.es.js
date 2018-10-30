export const formatFieldName = (instanceId, locale, value) => {
	return `ddm$$${value}$${instanceId}$0$$${locale}`;
};

export const generateInstanceId = length => {
	let text = '';

	const possible = 'ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789';

	for (let i = 0; i < length; i++) {
		text += possible.charAt(Math.floor(Math.random() * possible.length));
	}

	return text;
};