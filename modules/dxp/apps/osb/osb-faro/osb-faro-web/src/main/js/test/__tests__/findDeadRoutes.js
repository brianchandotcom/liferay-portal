import findDeadRoutes from '../findDeadRoutes';

describe('findDeadRoutes', () => {
	it('should return dead routes', () => {
		const routes = [
			'foo/:id',
			'foo/dead',
			'bar/:id(dead)',
			'bar/dead',
			'qux/:id/edit',
			'qux/dead/edit'
		];

		const deadRoutes = findDeadRoutes(routes);

		expect(deadRoutes).toEqual(['foo/dead', 'bar/dead', 'qux/dead/edit']);
	});

	it('should return no dead routes', () => {
		const routes = [
			'foo/bar',
			'foo/:id',
			'bar/:id(foo)',
			'bar/bar',
			'qux/:id/edit',
			'qux/bar'
		];

		const deadRoutes = findDeadRoutes(routes);

		expect(deadRoutes).toEqual([]);
	});

	it('should return dead route if both paths are dynamic', () => {
		const routes = ['foo/:id', 'foo/:name'];

		const deadRoutes = findDeadRoutes(routes);

		expect(deadRoutes).toEqual(['foo/:name']);
	});

	it('should return no dead routes with dynamic routes with regex that do not match', () => {
		const routes = ['foo/:id(\\d+)', 'foo/:name(hey)'];

		const deadRoutes = findDeadRoutes(routes);

		expect(deadRoutes).toEqual([]);
	});

	it('should return dead routes with dynamic routes with regex that do match', () => {
		const routes = ['foo/:id(\\d+)', 'foo/:name(123)'];

		const deadRoutes = findDeadRoutes(routes);

		expect(deadRoutes).toEqual(['foo/:name(123)']);
	});
});
