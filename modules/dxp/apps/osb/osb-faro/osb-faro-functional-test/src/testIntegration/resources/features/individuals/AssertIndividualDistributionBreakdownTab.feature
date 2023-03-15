@spira_Individuals @Individuals @Dashboard @team_FARO @priority_3
Feature: Assert breakdown by property can select different tabs
	As a Business User, I should be able to select different tabs in breakdown

	Background: [Setup] Add a breakdown by property
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page
		* I select the attribute "industry"
		* I name and save the breakdown "Industry"

	Scenario: Select different tab in breakdown
		Given I add another breakdown
		And I select the attribute "state"
		And I name and save the breakdown "State"
		When I click the "Industry" button
		Then I should see the following names in "Industry" breakdown:
			| architecture & planning  		|
			| food & beverages		   	|
			| philanthropy			   	|
			| international affairs	   		|
			| alternative medicine     		|
			| paper & forest products  		|
			| fund-raising             		|
			| research                 		|
			| semiconductors           		|
			| public relations and communications	|
		And I delete the breakdown
		And I delete the breakdown