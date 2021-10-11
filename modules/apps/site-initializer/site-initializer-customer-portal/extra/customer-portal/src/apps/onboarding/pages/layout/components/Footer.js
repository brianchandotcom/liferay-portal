const Footer = ({ leftButton, middleButton, rightButton }) => {
  return (
    <div className={`d-flex justify-content-${leftButton || rightButton ? "between" : "center"} p-4`}>
      { leftButton }
      { middleButton }
      { rightButton }
    </div>
  );
};

export default Footer;
