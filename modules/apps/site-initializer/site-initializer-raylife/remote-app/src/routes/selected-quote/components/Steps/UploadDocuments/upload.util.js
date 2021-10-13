const fileTypes = {
	'application/pdf': 'document-pdf',
	'application/vnd.openxmlformats-officedocument.presentationml.presentation':
		'document-presentation',
	'application/vnd.openxmlformats-officedocument.spreadsheetml.sheet':
		'document-table',
	'application/vnd.openxmlformats-officedocument.wordprocessingml.document':
		'document-text',
	'text/plain': 'document-text',
};

export const chooseIcon = (fileType) =>
	fileTypes[fileType] || 'document-unknown';

export const validateExtensions = (fileType, type) => {
	const validExtensions =
		type === 'image'
			? ['image/jpeg', 'image/jpg', 'image/png']
			: Object.keys(fileTypes);

	return validExtensions.includes(fileType);
};
