#Procedure to fix Core Upgrade Issues in Liferay 7
In previous Liferay versions was really common to add upgrade fixes into verify classes. That wasn't a good practice for two reasons:
- The nature of an upgrade fix is to be executed just once. However a verify process remains in the code (for next Liferay versions) to be executed as many times as necessary.
- These verify processes turn into classes really difficult to maintain since contain a bunch of upgrade fixes some of them old and useless for next Liferay versions.

To avoid this we have defined a new procedure to fix upgrade issues in the Core from Liferay 7 or DXP and above:

1. Fix the issue in master. To do it, you should add the upgrade fix to the upgrade process class associated to the next GA release. For example, if latest published GA release is 7.0 GA2 (7001), you should add your upgrade fix to _UpgradeProcess_7_0_2_ class, if that class doesn't exist yet, you should create it. This step will solve the issue for next GA upgrades.

2. Backport these changes to ee-7.0.x. Fix pack team should include these modifications to future fix packs. This will solve the issue for new EE customers who upgrade to DXP after installing this fix pack.

3. If it's a critical issue or your customer needs to solve the problem but they can't repeat the upgrade, you should create a new module into _modules/private/fixes/upgrade_ directory inside **liferay-portal-ee** (ee-7.0.x) branch. This module has to include a Gogo console command which exeuctes the same modifications than the upgrade process. You should create a knowledge base article with the instructions to execute the process so that other customers could use it in the future (add the related LPS issue to the documentation). As first reviewal, please, send the pull request to commit this module to the Upgrade SME (Alberto Chaparro).

As an example to perform this process, please check the commits for LPS-66599.
