import AdministratorIcon from "../assets/administratorIcon.svg";
import TicketCreatorIcon from "../assets/tiketCreatorIcon.svg";
import TicketWatcherIcon from "../assets/ticketWatcherIcon.svg";

const steps = {
  welcome: 0,
  invites: 1,
  dxp: 2,
};

const roles = {
  admin: {
    id: 1,
    name: "Administrator",
    icon: AdministratorIcon,
    description: "Administrator Description text about this role goes here.",
    responsibles: [
      "Managin Users & Roles",
      "Configuring the Plataform",
      "Setting up sites"
    ]
  },
  creator: {
    id: 2,
    name: "Ticket Creator",
    icon: TicketCreatorIcon,
    description: "Ticket Creator Description text about this role goes here.",
    responsibles: [
      "Managin Users & Roles",
      "Configuring the Plataform",
      "Setting up sites"
    ]
  },
  watcher: {
    id: 3,
    name: "Ticket Watcher",
    icon: TicketWatcherIcon,
    description: "Ticket Watcher Description text about this role goes here.",
    responsibles: [
      "Managin Users & Roles",
      "Configuring the Plataform",
      "Setting up sites"
    ]
  }
};

const getInitialInvite = (id = roles.watcher.id) => {
  return {
    email: "",
    roleId: id
  };
};

const getInitialDxpAdmin = () => {
  return {
    email: "",
    firstName: "",
    lastName: "",
    github: "",
  };
};

const getRolesList = () => Object.values(roles);


export { steps, roles, getInitialInvite, getInitialDxpAdmin, getRolesList };
