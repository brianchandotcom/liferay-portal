const Header = ({ greetings, title, helper }) => {
  return (
    <header className="p-4">
      {greetings && <h6 className="mb-1 text-brand-primary text-small-caps">{greetings}</h6>}
      <h2 className={`${helper ? "mb-1" : "mb-0"} text-neutral-0`}>{title}</h2>
      {helper && <p className="mb-0 text-neutral-3 text-paragraph-sm">{helper}</p>}
    </header>
  );
};

export default Header;
