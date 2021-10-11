import Footer from "./components/Footer";
import Header from "./components/Header";

const Layout = ({ children, headerProps, footerProps, mainStyles }) => (
  <div className="border d-flex flex-column mt-5 mx-auto onboarding rounded-lg shadow-lg">
    <Header {...headerProps} />
    <main className={`flex-grow-1 overflow-auto ${mainStyles ? mainStyles : ""}`}>{children}</main>
    <Footer {...footerProps} />
  </div>
);

export default Layout;
