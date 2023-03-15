@spira_Data_Source @Data_Source @DXP @Token @Property @team_FARO @priority_3
Feature: Assign site to property when properties exist
  	As a Business User, I should be able to assign a site to property when properties exist

  	Background: [Setup] Create a property
	  	* I go to the "Home" page
	  	* I login as "test@faro.io:test"
	  	* I should see the "Sites" page
	  	* I go to the "Properties" page
	  	* I create a new Property named "ExistingSiteProperty"
	  	* I go to the "Data Source" page
	  	* I click the "Add Data Source" button
	  	* I click the "Liferay DXP" button
	  	* I click the "Connect with Token" button
	  	* I copy the DXP Authentication Token
	  	* I set up the local DXP instance
	  	* I connect Analytics Cloud to DXP using the Authentication Token

	Scenario: Add site to existing property
	  	Given I go to the "Synced Sites" DXP Page
	  	And I should see Property named "ExistingSiteProperty" in the table
		When I click existing property "ExistingSiteProperty" in synced sites
	  	And I select DXP site "Liferay DXP"
	  	Then I should see an success alert saying "Success:Your request completed successfully"
