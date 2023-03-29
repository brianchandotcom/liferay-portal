import {debounce} from 'lodash';

export default function debounceDecorate(...args) {
	return (target, key, descriptor) => {
		const {get} = descriptor;

		return {
			get() {
				const value = get ? get.apply(this) : descriptor.value;

				const debounceFn = debounce(value, ...args);

				Object.defineProperty(this, key, {
					get() {
						return debounceFn;
					}
				});

				return debounceFn;
			}
		};
	};
}
