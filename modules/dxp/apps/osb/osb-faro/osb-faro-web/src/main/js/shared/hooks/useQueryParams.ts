import {parse} from 'query-string';
import {useLocation} from 'react-router-dom';

// TODO: Remove this once we upgrade to react-router-dom v6
export const useQueryParams = () => {
	const {search} = useLocation();

	return parse(search);
};
