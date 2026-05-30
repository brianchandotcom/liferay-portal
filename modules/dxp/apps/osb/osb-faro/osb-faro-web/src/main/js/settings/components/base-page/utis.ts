import {NavigateFunction} from 'react-router-dom';

export function updateSearchParams(
	navigate: NavigateFunction,
	key: string,
	value: any
) {
	const params = new URLSearchParams(window.location.search);

	params.set(key, String(value));

	navigate({
		pathname: window.location.pathname,
		search: params.toString()
	});
}
