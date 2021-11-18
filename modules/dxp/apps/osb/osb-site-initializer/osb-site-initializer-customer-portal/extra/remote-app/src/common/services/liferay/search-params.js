const liferaySearchParams = new URLSearchParams(window.location.search);

export const SearchParams = {
	get: (param) => liferaySearchParams.get(param),
};

export const PARAMS_KEYS = {
	PROJECT_APPLICATION_EXTERNAL_REFERENCE_CODE: 'kor_id',
};
