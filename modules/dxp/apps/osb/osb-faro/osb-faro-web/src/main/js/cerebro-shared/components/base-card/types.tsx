export type Router = {
	params: object;
	query: object;
};

export type Context = {
	filters: object;
	router: Router;
};
