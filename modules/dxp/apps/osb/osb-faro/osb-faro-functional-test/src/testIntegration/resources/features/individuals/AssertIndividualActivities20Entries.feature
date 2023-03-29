@spira_Individuals @Individuals @team_FARO @priority_3
Feature: Individual activities shows last 20 entries
	As a Business User, I should be able to see the last 20 activity entries of an individual

	Background: [Setup] Navigate to Individuals page
		* I go to the "Home" page
		* I login as "test@faro.io:test"
		* I should see the "Sites" page
		* I go to the "Individuals" page

	Scenario: Assert activities show last 20 entries
		Given I click the "Known Individuals" tab
		And I search for "Annita Nader"
		When I click "Annita Nader" in the table
		Then I should see pagination delta is set to "20"
		And I should see activities named "Visited www.sherman-pfeffer.net" in the table
	  	And I should see activities named "Visited www.cherelle-ullrich.org" in the table