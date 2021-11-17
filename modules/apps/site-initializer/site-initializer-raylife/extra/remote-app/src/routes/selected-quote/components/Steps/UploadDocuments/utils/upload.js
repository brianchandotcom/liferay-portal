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

export function chooseIcon(fileType) {
	return fileTypes[fileType] || 'document-unknown';
}

export function validateExtensions(fileType, type) {
	const validExtensions =
		type === 'image'
			? ['image/jpeg', 'image/jpg', 'image/png']
			: Object.keys(fileTypes);

	return validExtensions.includes(fileType);
}

export const sectionsHasError = (sections) => {
	return sections.some((section) => section.error);
};
