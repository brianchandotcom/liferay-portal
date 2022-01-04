const {REACT_APP_LIFERAY_API = window.location.origin} = process.env;

const API_BASE_URL = REACT_APP_LIFERAY_API;

export {API_BASE_URL};

const getCurrentEndDate = (currentEndDate) => {
	const date = new Date(currentEndDate);
	const month = date.toLocaleDateString('default', {month: 'short'});
	const day = date.getDate();
	const year = date.getFullYear();

	return `${month} ${day}, ${year}`;
};

const downloadFromBlob = (blob, filename) => {
	const a = document.createElement('a');
	a.href = window.URL.createObjectURL(blob);
	a.download = filename;
	document.body.appendChild(a);
	a.click();
	a.remove();
};

export {getCurrentEndDate, downloadFromBlob};
