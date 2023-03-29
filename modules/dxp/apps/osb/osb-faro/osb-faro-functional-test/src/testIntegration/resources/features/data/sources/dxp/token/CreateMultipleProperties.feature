@spira_Data_Source @Data_Source @DXP @Token @Property @team_FARO @priority_3 @prototype
Feature: Assign sites to property and create multiple properties
  	As a Business User, I should be able to create multiple properties with multiple sites

  	Background: [Setup] Add additional site and connect DXP
	  	* I go to the "Home" page
	  	* I login as "test@faro.io:test"
	  	* I should see the "Sites" page
		* I go to the "Data Source" page
	  	* I click the "Add Data Source" button
	  	* I click the "Liferay DXP" button
	  	* I click the "Connect with Token" button
	  	* I copy the DXP Authentication Token
	  	* I set up the local DXP instance
	  	* I connect Analytics Cloud to DXP using the Authentication Token
	  	* I create a new Site named "Multiple site" in DXP

	Scenario: Create multiple properties
	  	Given I go to the "Synced Sites" DXP Page
	  	When I create a new Multiple Property with the following Sites on DXP:
	  		| Liferay DXP | Multiple site |
	  	And I go to the "Home" page
	  	And I login as "test@faro.io:test"
	  	And I go to the "Properties" page
	  	Then I should see a Property named "Liferay DXP" in the table
	  	And I should see a Property named "Multiple site" in the table