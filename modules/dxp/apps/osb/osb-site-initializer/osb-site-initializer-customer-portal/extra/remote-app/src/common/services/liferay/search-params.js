const liferaySearchParams = new URLSearchParams(window.location.search);

export const get = (param) => {
	return liferaySearchParams.get(param);
};

export const SearchParams = {
	get
};

export const PARAMS_KEYS = {
	PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE: 'kor_id',
};