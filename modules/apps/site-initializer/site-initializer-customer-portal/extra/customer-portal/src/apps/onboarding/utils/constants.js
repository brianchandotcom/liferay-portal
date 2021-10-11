import AdministratorIcon from '../assets/administratorIcon.svg';
import TicketWatcherIcon from '../assets/ticketWatcherIcon.svg';
import TicketCreatorIcon from '../assets/tiketCreatorIcon.svg';

const steps = {
	dxp: 2,
	invites: 1,
	welcome: 0,
};

const roles = {
	admin: {
		description:
			'Administrator Description text about this role goes here.',
		icon: AdministratorIcon,
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
		icon: TicketCreatorIcon,
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
		icon: TicketWatcherIcon,
		id: 3,
		name: 'Ticket Watcher',
		responsibles: [
			'Managin Users & Roles',
			'Configuring the Plataform',
			'Setting up sites',
		],
	},
};

const getInitialInvite = (id = roles.watcher.id) => {
	return {
		email: '',
		roleId: id,
	};
};

const getInitialDxpAdmin = () => {
	return {
		email: '',
		firstName: '',
		github: '',
		lastName: '',
	};
};

const getRolesList = () => Object.values(roles);

export {steps, roles, getInitialInvite, getInitialDxpAdmin, getRolesList};
