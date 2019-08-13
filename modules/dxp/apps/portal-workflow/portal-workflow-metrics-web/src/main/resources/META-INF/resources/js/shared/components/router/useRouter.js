import {__RouterContext as RouterContext} from 'react-router-dom';
import {useContext} from 'react';

const useRouter = () => {
	return useContext(RouterContext);
};

export {useRouter};
