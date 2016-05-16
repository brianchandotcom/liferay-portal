# Portal Tools - DB Upgrade Client

The DB Upgrade Client is a standalone client used for upgrading your Liferay DXP
database.

## Installation

To install and execute the DB Upgrade Client, there are two methods you can
follow.

### Extracting the Downloaded ZIP:

1. Deploy Liferay to an application server

2. Download the latest version of the DB Upgrade Client

3. Unzip the client into your Liferay instance
(e.g., *${liferayHome}/tools/portal-tools-db-upgrade-client*)

4. Run *java -jar com.liferay.portal.tools.db.upgrade.client.jar*

### Using JPM:

1. Install [JPM](https://jpm4j.org)

2. Run *jpm install com.liferay.portal.tools.db.upgrade.client*

3. Run *liferay-upgrade-db*

Both methods have separate ways of executing the tool. The command line options
listed in the next section, however, can be appended the same way for both
methods (e.g., *liferay-upgrade-db --noShell*).

## Usage

The first time you run the tool, it will automatically configure properties for
your installation of Liferay. You can also do this manually by configuring
*app-server.properties*, *portal-upgrade-database.properties*, and
*portal-upgrade-ext.properties*. Make sure to place these *.properties* files in
the same directory from which you execute the tool.

The DB Upgrade Client also connects to the Gogo shell automatically after the
upgrade if there were any issues. This lets you check the status of any module
upgrades that failed to complete.

**Note:** You can configure the portal to only upgrade the core, and not
external modules by adding a file called
*com.liferay.portal.upgrade.internal.configuration.ReleaseManagerConfiguration.cfg*
in the *osgi/configs/* folder with the following content: *autoUpgrade=false*.

You can reference options for command line execution, Gogo shell commands, and
configuration below.

### Command Line Options

- **--jvm-opts** or **-j** - sets any JVM options for the upgrade process

- **--log-file** or **-l** - uses a custom name for your log file

- **--shell** or **-s** - connects to GoGo Shell after upgrades are completed

### Gogo Shell Command Options

- **exit** or **quit** - exits the Gogo shell

- **upgrade:help** - displays upgrade commands

- **upgrade:check** - lists upgrades that need have not started or failed to
complete

- **upgrade:execute _{module_name}_** - executes upgrade for that module

- **upgrade:list** - lists all registered upgrades

- **upgrade:list _{module_name}_** - lists the upgrade steps required for that
module

- **upgrade:list | grep Registered** - lists registered upgrades and their
versions

- **upgrade:list | grep Registered | grep steps** - lists registered upgrades in
progress

- **verify:execute _{module_name}_** - executes a verifier

- **verify:list** - lists all registered verifiers

### Configuration Options

#### app-server.properties

- **dir** - the application server directory *(required)*

- **extra.lib.dirs** - a comma delimited list of extra directories you want to
add to the classpath *(required)*

- **global.lib.dir** - the global lib directory of your application server
*(required)*

- **portal.dir** - the portal directory *(required)*

#### portal-upgrade-database.properties

- **jdbc.default.driverClassName** *(required)*

- **jdbc.default.url** *(required)*

- **jdbc.default.username** *(required)*

- **jdbc.default.password** *(required)*

#### portal-upgrade-ext.properties

- **liferay.home** - the Liferay home directory *(required)*

- **hibernate.jdbc.batch_size** - the JDBC batch size used to improve
performance; set to *250* by default *(optional)*