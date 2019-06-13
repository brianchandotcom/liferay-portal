import {createContext, useState} from 'react';

const useErrors = () => {
	const [errors, setErrors] = useState({});

	return {errors, setErrors};
};

const Errors = createContext({});

export {Errors, useErrors};