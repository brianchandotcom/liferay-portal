export const pipe = (...fns) => (initialVal) => fns.reduce((prevVal, currentFn) => currentFn(prevVal), initialVal);
export const compose = (...fns) => (initialVal) => fns.reduceRight((prevVal, currentFn) => currentFn(prevVal), initialVal);
