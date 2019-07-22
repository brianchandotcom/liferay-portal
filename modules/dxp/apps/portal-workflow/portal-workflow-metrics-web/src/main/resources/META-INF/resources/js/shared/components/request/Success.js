import {ErrorContext} from './Error';
import {LoadingContext} from './Loading';
import {useContext} from 'react';

function Success({children}) {
	const {error} = useContext(ErrorContext);
	const {loading} = useContext(LoadingContext);

	return !error && !loading && children;
}

export {Success};
