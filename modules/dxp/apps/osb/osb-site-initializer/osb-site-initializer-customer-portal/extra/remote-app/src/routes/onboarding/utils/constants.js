const roles = {
	admin: {
		description:
			'Administrator Description text about this role goes here.',
		id: 1,
		name: 'Administrator',
		responsibles: [
			'Managin Users & Roles',
			'Configuring the Plataform',
			'Setting up sites',
		],
	},
	creator: {
		description:
			'Ticket Creator Description text about this role goes here.',
		id: 2,
		name: 'Ticket Creator',
		responsibles: [
			'Managin Users & Roles',
			'Configuring the Plataform',
			'Setting up sites',
		],
	},
	watcher: {
		description:
			'Ticket Watcher Description text about this role goes here.',
		id: 3,
		name: 'Ticket Watcher',
		responsibles: [
			'Managin Users & Roles',
			'Configuring the Plataform',
			'Setting up sites',
		],
	},
};

const steps = {
	dxp: 2,
	invites: 1,
	welcome: 0,
};

const getInitialDxpAdmin = () => ({
	email: '',
	firstName: '',
	github: '',
	lastName: '',
});

const getInitialInvite = (id = roles.watcher.id) => ({
	email: '',
	roleId: id,
});

const getRoles = () => Object.values(roles);

export {steps, roles, getInitialInvite, getInitialDxpAdmin, getRoles};
