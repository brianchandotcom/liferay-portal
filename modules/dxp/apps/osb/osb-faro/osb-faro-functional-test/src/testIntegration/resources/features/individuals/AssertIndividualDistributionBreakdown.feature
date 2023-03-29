@spira_Individuals @Individuals @Dashboard @team_FARO @priority_4
Feature: Assert the Individual Distribution card
	As a Business User, I'd like to customize the distribution card to have multiple tabs of breakdowns that are important to me

	Background: [Setup]
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Add a distribution breakdown
		When I select the attribute "industry"
		And I name and save the breakdown "Industry"
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

	Scenario: Assert distribution name can't be duplicated
		When I add another breakdown
		And I select the attribute "industry"
		And I name the breakdown "Industry"
		Then I should not be able to click the "Save" button
		And I should see the name already exists

	Scenario: Remove a distribution breakdown
		When I delete the breakdown
		Then I should see the breakdown is deleted