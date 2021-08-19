const body = document.querySelector('body');

const navbarButton = fragmentElement.querySelector('.navbar-toggler');
const navbarCollapse = fragmentElement.querySelector('.navbar-collapse');
const siteNavbar = fragmentElement.querySelector('.bootcamp-navbar');

navbarButton.addEventListener('click', function () {
  navbarCollapse.classList.toggle('show');
  siteNavbar.classList.toggle('open');
  body.classList.toggle('overflow-hidden');

  navbarButton.setAttribute(
    'aria-expanded',
    navbarCollapse.classList.contains('show').toString()
  );
});
