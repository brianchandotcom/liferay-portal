@spira_Accounts @Accounts @team_FARO @priority_3
Feature: Assert account activities graph selected on first load
  	As a Business User, I should be able to assert account activities graph selected on first load

  	Background: [Setup] Navigate to Accounts page
	  	* I go to the "Home" page
	  	* I login as "test@faro.io:test"
	  	* I should see the "Sites" page
	  	* I go to the "Accounts" page

	Scenario: Activities graph selected on first load
	  	Given I click "Hilll, Gottlieb and Dicki" in the table
	  	When I click the "Activities" tab
	  	Then I should see "Activities" graph selected