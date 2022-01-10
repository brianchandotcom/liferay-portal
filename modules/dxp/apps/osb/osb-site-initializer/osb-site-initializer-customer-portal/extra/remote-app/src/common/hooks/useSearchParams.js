import {useMemo} from 'react';

const useSearchParams = () => {
	const searchParams = useMemo(
		() => new URLSearchParams(window.location.search),
		[]
	);

	return {
		get: (param) => searchParams.get(param),
	};
};

export default useSearchParams;
