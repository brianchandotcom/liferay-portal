import Pages from "./pages";
import CustomerPortalStyles from "~/apps/customer-portal/styles/app.scss";

const CustomerPortal = () => {
  return (
    <>
      <style>{CustomerPortalStyles}</style>
      <Pages />
    </>
  );
};

export default CustomerPortal;
