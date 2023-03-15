import {parse} from 'query-string';
import {useLocation} from 'react-router-dom';

// TODO: Remove this once we upgrade to react-router-dom v6
const useQueryParams = () => {
	const {search} = useLocation();

	return parse(search);
};

export default useQueryParams;
